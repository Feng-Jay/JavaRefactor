package utils;

import java.util.HashMap;
import java.util.Map;

import static utils.LLogger.logger;

public class Scope {
    public Map<String, String> _varMapping;
    private Scope _parent;

    public Scope(){
        _varMapping = new HashMap<>();
    }

    public void setParent(Scope scope){
        this._parent = scope;
    }

    public Scope getParent(){
        return this._parent;
    }

    public void addVar(String oriVar, String newVar){
        if(_varMapping.containsKey(oriVar)){
            logger.error("Wrong when renaming variables!!!");
            System.exit(-1);
        }else{
            _varMapping.put(oriVar, newVar);
        }
    }

    public String haveVar(String oriVar){
        Scope tmp = this;
        while(tmp != null){
            if(tmp._varMapping.containsKey(oriVar)){
                return tmp._varMapping.get(oriVar);
            }else{
                tmp = tmp.getParent();
            }
        }
        return "";
    }
}
