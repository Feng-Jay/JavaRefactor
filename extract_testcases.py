import json
from tqdm import tqdm
from pathlib import Path

single_function_repair_file = "./d4j-info/single_function_repair.json"
single_function_repair_path = Path(single_function_repair_file)

bugs = json.load(single_function_repair_path.open('r'))

def make_fakeClass(method):
    ret = "public class FAKECLASS{\n" + method + "\n}"
    return ret

out_file_format = "./src/test/resources/{idx}_{buggy}.java"

for idx, bug_id in tqdm(enumerate(bugs.keys())):
    proj, id = bug_id.split("-")
    
    buggy_code = bugs[bug_id]["buggy"]
    fixed_code = bugs[bug_id]["fix"]

    fake_buggy_class = make_fakeClass(buggy_code)
    fake_fixed_class = make_fakeClass(fixed_code)

    out_buggy_file = Path(out_file_format.format(idx=idx, buggy="b"))
    out_fixed_file = Path(out_file_format.format(idx=idx, buggy="f"))

    with out_buggy_file.open("w") as f:
        f.write(fake_buggy_class)
    
    with out_fixed_file.open("w") as f:
        f.write(fake_fixed_class)
    
