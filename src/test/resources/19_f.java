public class FAKECLASS{
    public List getValues(final Option option,
                          List defaultValues) {
        // initialize the return list
        List valueList = (List) values.get(option);

        // grab the correct default values
        if (defaultValues == null || defaultValues.isEmpty()) {
            defaultValues = (List) this.defaultValues.get(option);
        }

        // augment the list with the default values
        if (defaultValues != null && !defaultValues.isEmpty()) {
            if (valueList == null || valueList.isEmpty()) {
                valueList = defaultValues;
            } else {
                // if there are more default values as specified, add them to
                // the list.
                if (defaultValues.size() > valueList.size()) {
                    // copy the list first
                    valueList = new ArrayList(valueList);
                    for (int i=valueList.size(); i<defaultValues.size(); i++) {
                        valueList.add(defaultValues.get(i));
                    }
                }
            }
        }
        
        return valueList == null ? Collections.EMPTY_LIST : valueList;
    }
}