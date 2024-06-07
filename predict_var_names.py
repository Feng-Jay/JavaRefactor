import torch
from accelerate import init_empty_weights, infer_auto_device_map, load_checkpoint_in_model, dispatch_model
from transformers import AutoConfig, AutoModelForCausalLM, AutoTokenizer, StoppingCriteria, StoppingCriteriaList, \
    LlamaForCausalLM, CodeLlamaTokenizer


class End_of_function_criteria(StoppingCriteria):
    def __init__(self, start_length, eof_strings, tokenizer):
        self.start_length = start_length
        self.eof_strings = eof_strings
        self.tokenizer = tokenizer
        self.end_length = {}

    def __call__(self, input_ids, scores, **kwargs) -> bool:
        """Returns true if all generated sequences contain any of the end-of-function strings."""
        decoded_generations = self.tokenizer.batch_decode(input_ids[:, self.start_length:])
        done = []
        for index, decoded_generation in enumerate(decoded_generations):
            finished = any([stop_string in decoded_generation for stop_string in self.eof_strings])
            if finished and index not in self.end_length:  # ensures first time we see it
                for stop_string in self.eof_strings:
                    if stop_string in decoded_generation:
                        self.end_length[index] = len(input_ids[index,  # get length of actual generation
                                                     self.start_length:
                                                     -len(self.tokenizer.encode(stop_string, add_special_tokens=False,
                                                                                return_tensors='pt')[0])])
            done.append(finished)
        return all(done)


global_eof_stops = ['// Buggy Function', '// Fixed Function', '# Buggy Function', '# Fixed Function',
                    '/* Buggy Function */', '/* Fixed Function */', '<|endoftext|>', '<｜end▁of▁sentence｜>', '<｜end▁of▁sentence｜>']


class GeneralLLM(object):
    def __init__(self, batch_size: int = 1, pretrained: str = 'starcoder', stop='', weight=None, parameter_dir=None) -> None:
        print("Initializing  model from {}...".format(parameter_dir))
        self.devices = torch.device("cuda" if torch.cuda.is_available() else "cpu")
        self.device_ids = [0]
        self.model = AutoModelForCausalLM.from_pretrained(parameter_dir)
        self.tokenizer = AutoTokenizer.from_pretrained(parameter_dir)
        if weight == "float16":
            print("Changing to float 16...")
            self.model = self.model.half()
        elif weight == "bfloat16":
            print("Switching to bfloat16 ...")
            self.model = self.model.to(torch.bfloat16)
        self.model = self.model.to(self.device_ids[0])
        self.max_length = 4096
        if 'max_position_embeddings' in self.model.config.to_dict():
            self.max_length = self.model.config.to_dict()['max_position_embeddings']
        elif 'n_positions' in self.model.config.to_dict():
            self.max_length = self.model.config.to_dict()['n_positions']

        print("Max length: {}".format(self.max_length))

        self.model = torch.nn.DataParallel(self.model, device_ids=self.device_ids)
        self.stop = stop
        self.batch_size = batch_size

    def check_input(self, prompt: str, buggy_func: str) -> bool:
        input_tokens = self.tokenizer.encode(prompt + "\n" + buggy_func, return_tensors='pt')
        if len(input_tokens[0]) > self.max_length:
            return False
        return True

    def model_prediction(self, prompt: str, buggy_func: str, do_sample=False, num_samples=10000):
        if not self.check_input(prompt, buggy_func):  # input too long
            return False, False, None, None
        input_tokens = self.tokenizer.encode(prompt, return_tensors='pt').repeat(min(self.batch_size, num_samples),
                                                                                 1)
        input_tokens = input_tokens.to(self.device_ids[0])

        sc = StoppingCriteriaList(
            [End_of_function_criteria(len(input_tokens[0]), [self.stop] + global_eof_stops, self.tokenizer)])
        with torch.no_grad():
            raw_o = self.model.module.generate(input_ids=input_tokens,
                                               max_length=min(self.max_length, len(input_tokens[0]) +
                                                              int(2 * len(self.tokenizer.encode(buggy_func,
                                                                                                return_tensors='pt')[
                                                                              0]))),
                                               stopping_criteria=sc,
                                               do_sample=do_sample,
                                               top_p=0.95,
                                               temperature=0.8,
                                               output_scores=True,
                                               return_dict_in_generate=True,
                                               pad_token_id=self.tokenizer.eos_token_id
                                               )
            gen_sequences = raw_o.sequences[:, len(input_tokens[0]):]
            neg_logs = -torch.log(torch.stack(raw_o.scores, dim=1).softmax(-1))
            neg_logs = torch.gather(neg_logs, 2, gen_sequences[:, :, None]).squeeze(-1)
            t_outputs = self.tokenizer.batch_decode(gen_sequences, skip_special_tokens=False)
            # print(t_outputs)
            outputs = []
            entropies = []
            for index, output in enumerate(t_outputs):
                min_index = 10000000
                for eof_string in [self.stop] + global_eof_stops:
                    if eof_string in output:
                        min_index = min(output.index(eof_string), min_index)
                        if index not in sc[0].end_length:
                            sc[0].end_length[index] = len(gen_sequences[index,
                                                          :-len(self.tokenizer.encode(eof_string,
                                                                                      add_special_tokens=False,
                                                                                      return_tensors='pt')[0])])

                if min_index != 10000000 and sc[0].end_length[index] != 0:
                    outputs.append(output[:min_index].strip())
                    entropies.append(
                        (neg_logs[index, :sc[0].end_length[index]].sum(-1).cpu().item() / sc[0].end_length[index],
                         neg_logs[index, :sc[0].end_length[index]].sum(-1).cpu().item()))

        return True, len(outputs) > 0, outputs, entropies


def predict_var_name(prompt):



if __name__ == "__main__":
        