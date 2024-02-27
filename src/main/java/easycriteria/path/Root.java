package easycriteria.path;

import jakarta.persistence.metamodel.Attribute;
import lombok.Getter;

public class Root<X> extends CriteriaPath {

    @Getter
    private Attribute[] arrayAttribute;

    private Root(PathType type, Attribute... attribute) {
        this.type = type;
        this.arrayAttribute = attribute;
    }

    public static <X> Root<X> path(Attribute<?, X> attribute) {
        return new Root(PathType.ROOT, attribute);
    }

    public static <X> Root<X> max(Attribute<?, X> attribute) {
        return new Root(PathType.MAX, attribute);
    }

    public static <X> Root<X> min(Attribute<?, X> attribute) {
        return new Root(PathType.MIN, attribute);
    }

    public <Y> Root<Y> chain(Attribute<X, Y> attribute) {
        Attribute[] newArray = new Attribute[this.arrayAttribute.length + 1];
        System.arraycopy(this.arrayAttribute, 0, newArray, 0, this.arrayAttribute.length);
        newArray[this.arrayAttribute.length] = attribute;
        return new Root(this.type, newArray);
    }

    public Class<X> getJavaType() {
        Attribute<?, X> endAttribute = arrayAttribute[this.arrayAttribute.length-1];
        return endAttribute.getJavaType();
    }
}
