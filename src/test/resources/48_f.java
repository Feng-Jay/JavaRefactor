public class FAKECLASS{
        protected JSType caseTopType(JSType topType) {
          return topType.isAllType() ?
              getNativeType(ARRAY_TYPE) : topType;
        }
}