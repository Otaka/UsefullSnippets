/**
 * Class that similar to StringBuilder, but simplify adding separators when you output elements in loop to string
 */
public class StringBuilderWithSeparator {

    private StringBuilder sb;
    private boolean newEntry;
    private String separator = ",";

    public StringBuilderWithSeparator() {
        this.newEntry = false;
        this.sb = new StringBuilder();
    }

    public StringBuilderWithSeparator(String separator) {
        this();
        this.separator = separator;
    }

    public StringBuilderWithSeparator appendWithoutSeparator(String value) {
        sb.append(value);
        return this;
    }

    public StringBuilderWithSeparator append(int value) {
        return append(Integer.toString(value));
    }

    public StringBuilderWithSeparator append(Object value){
        return append(value==null?"null":value.toString());
    }
            
    
    public StringBuilderWithSeparator append(String value) {
        if (newEntry) {
            sb.append(separator);
            newEntry = false;
        }

        sb.append(value);
        return this;
    }

    public StringBuilderWithSeparator newEntry() {
        newEntry = true;
        return this;
    }
    
    public StringBuilderWithSeparator skipNewEntry(){
        newEntry=false;
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

}