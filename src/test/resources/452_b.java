public class FAKECLASS{
    public Class getGenericType(Field field) {        
        Type generic = field.getGenericType();
        if (generic != null && generic instanceof ParameterizedType) {
            Type actual = ((ParameterizedType) generic).getActualTypeArguments()[0];
                return (Class) actual;
                //in case of nested generics we don't go deep
        }
        
        return Object.class;
    }
}