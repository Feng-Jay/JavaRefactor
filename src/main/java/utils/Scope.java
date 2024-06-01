package utils;

import java.util.HashMap;
import java.util.Map;

import static utils.LLogger.logger;

public class Scope {
    public Map<String, String> _varMapping;

    public String _name = "";
    public int _beginLine = 0;
    public int _endLine = 0;
    private Scope _parent;

    public Scope(){
        _varMapping = new HashMap<>();
    }

    public Scope(int beginLine, int endLine){
        this();
        this._beginLine = beginLine;
        this._endLine = endLine;
    }

    public Scope(String name, int beginLine, int endLine){
        this(beginLine, endLine);
        _name = name;
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

    public Boolean haveVar(String oriVar){
        Scope tmp = this;
        while(tmp != null){
            if(tmp._varMapping.containsKey(oriVar)){
                return true;
            }else{
                tmp = tmp.getParent();
            }
        }
        return false;
    }

    public String getReplaceName(String oriVar){
        Scope tmp = this;
        while (tmp != null){
            if(tmp._varMapping.containsKey(oriVar)){
                return tmp._varMapping.get(oriVar);
            }else{
                tmp = tmp.getParent();
            }
        }
        return null;
    }

    public String toString(){
        String output = _name.isEmpty() ? "" : _name;
        output += ";" + _beginLine + ";" + _endLine + "\n";
        output += _varMapping.keySet().toString();
        return output;
    }
}
