public class FAKECLASS{
    public String generateToolTipFragment(String toolTipText) {
        return " title=\"" + ImageMapUtilities.htmlEscape(toolTipText) 
            + "\" alt=\"\"";
    }
}