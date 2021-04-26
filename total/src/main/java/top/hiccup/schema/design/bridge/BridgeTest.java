package top.hiccup.schema.design.bridge;

/**
 * 桥接模式：将抽象与实现解耦，使得二者可以独立变化
 *
 * @author wenhy
 * @date 2020/8/16
 */
public class BridgeTest {

    public static interface Peppery {
        // 面条的不同辣味
        public String getStyle();
    }

    public static class PepperyStyle implements Peppery {

        @Override
        public String getStyle() {
            return "地狱辣";
        }
    }

    public static class PlainStyle implements Peppery {

        @Override
        public String getStyle() {
            return "清淡微辣";
        }
    }

    public static abstract class AbstractNoodle {
        // 分离出辣味的变化
        protected  Peppery style;

        public AbstractNoodle(Peppery style) {
            this.style = style;
        }

        public abstract void eat();
    }

    public static class PorkyNoodle extends AbstractNoodle {

        public PorkyNoodle(Peppery style) {
            super(style);
        }

        @Override
        public void eat() {
            System.out.println("猪肉面条+" + style.getStyle()
            );
        }
    }

    public static class BeefNoodle extends AbstractNoodle {

        public BeefNoodle(Peppery style) {
            super(style);
        }

        @Override
        public void eat() {
            System.out.println("牛肉面条+" + style.getStyle()
            );
        }
    }

    public static void main(String[] args) {
        AbstractNoodle noodle = new PorkyNoodle(new PepperyStyle());
        noodle.eat();
        noodle = new BeefNoodle(new PlainStyle());
        noodle.eat();
        noodle = new PorkyNoodle(new PlainStyle());
        noodle.eat();
    }
}
