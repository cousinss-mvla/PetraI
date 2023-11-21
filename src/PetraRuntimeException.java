public abstract class PetraRuntimeException extends RuntimeException {
    private final Global g;

    public PetraRuntimeException(Global g, String message) {
        super(message);
        this.g = g;
    }
    public Global getGlobal() {
        return this.g;
    }
}
