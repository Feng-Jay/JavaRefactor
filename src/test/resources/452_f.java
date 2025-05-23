public class FAKECLASS{
    public Class getGenericType(Field field) {        
        Type generic = field.getGenericType();
        if (generic != null && generic instanceof ParameterizedType) {
            Type actual = ((ParameterizedType) generic).getActualTypeArguments()[0];
            if (actual instanceof Class) {
                return (Class) actual;
            } else if (actual instanceof ParameterizedType) {
                //in case of nested generics we don't go deep
                return (Class) ((ParameterizedType) actual).getRawType();
            }
        }
        
        return Object.class;
    }
}