public abstract class EntityDescribeMethod {
    //whether this entity is taking over the Description runtime in this context. This method should not do anything, just return true or false given the context.
    //needed (much later): arguments? //TODO
    //one of the arguments should definitely be RContextFlag because of Rooms (must either have Description.dLong or a method descriptor)
    public abstract boolean isOverrideDescribeMethod();

    //given that {@link #isOverrideDescribeMethod} returned true, take over describing the object. generally should TELL, but can also do things
    //still needed: arguments? //TODO
    public abstract boolean describeMethod();
}
