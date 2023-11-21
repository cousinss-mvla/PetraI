public class Action {
    private final Token[] syntax; //for use with Token...
    final Token dirToken;
    final Token indToken;
    private final Method method;
    //optional
    private final Method preMethod;

    public Action(Method method, Method preMethod, Token... syntax) {
        this.method = method;
        this.preMethod = preMethod;
        this.syntax = syntax;
        Token dirTokenTemp = null;
        for(Token t : this.syntax) {
            if (t.type.equals(Token.TokenType.DIR)) {
                dirTokenTemp = t;
                break;
            }
        }
        this.dirToken = dirTokenTemp;
        Token indTokenTemp = null;
        for(Token t : this.syntax) {
            if (t.type.equals(Token.TokenType.IND)) {
                indTokenTemp = t;
                break;
            }
        }
        this.indToken = indTokenTemp;
    }

    public Action(Method method, Token... syntax) {
        this(method, null, syntax);
    }

    public Method getMethod() {
        return this.method;
    }

    public Method getPreMethod() {
        return this.preMethod;
    }
}
