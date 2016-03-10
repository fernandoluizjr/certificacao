package cap3.generics;

import java.util.List;
import java.util.ArrayList;

public class TestGenerics {
	public static void main(String[] args) {
    Glass<Juice> glassJuice = new Glass<>(new Juice()); //ok it implements Liquid
    //Glass<Cake> glassCake = new Glass<>(new Cake()); //nok it does not implement Liquid
    System.out.println(glassJuice.getLiquid());
    //System.out.println(glassJuice.getLiquidTaste(new Water())); // water has no taste
    System.out.println(glassJuice.getLiquidTaste(new Juice()));

    GlassOnlyOfFruitJuices<OrangeJuice> glassOnlyOrangeJuice = new GlassOnlyOfFruitJuices<>(); //ok orange implements fruit
    //GlassOnlyOfFruitJuices<AppleJuice> glassOnlyAppleJuice = new GlassOnlyOfFruitJuices<>(); //nok apple does not implement fruit

    //Glass<Liquid> glassLiquid = new Glass<Juice>(); //nok cuz both type inheritance from Object here
    //SuperGlass<Juice> glassPolymorphic = new ChieldGlass<Water>(); //nok cuz the types are different
    SuperGlass<Juice> glassPolymorphic = new ChieldGlass<Juice>();

    Tray tray = new Tray();
    tray.add(new WildGlass<Juice>());
    tray.add(new WildGlass<Cake>());

    tray.addOnlyJuice(new WildGlass<Juice>());//ok
    tray.addOnlyJuice(new WildGlass<OrangeJuice>()); //ok orangejuice is subtype of juice
    //tray.addOnlyJuice(new WildGlass<Cake>());//nok cake is not a juice
    tray.showContent();
  }
}

class Juice implements Liquid { 
  @Override public String toString() {return "Juice";}
  @Override public String taste() {return "sweet";}
}
class Water implements Liquid {
  @Override public String toString() {return "Water";}
  @Override public String taste() {return null;} //water has no taste
}
class Cake {
  @Override public String toString() {return "Cake";}
}

interface Liquid {
  String taste();
}

class Glass<T extends Liquid> {
  private T liquid;

  Glass(T liquid) {
    this.liquid = liquid;
  }

  T getLiquid() {
    return liquid;
  }

  // this method exemplify the use generics with bounds on method level
  // why? to protect we get a taste for water for example that has no taste
  public <U extends Juice> String getLiquidTaste(U juice) {
    return juice.taste();
  }
}

interface Fruit {}
class OrangeJuice extends Juice implements Fruit {}
class AppleJuice extends Juice {}

// when using multiple type parameters you must put the concrete types first
class GlassOnlyOfFruitJuices<T extends Juice & Fruit> {
}

class SuperGlass<T> {
}

// this assert that both types (super and sub) will receive the same type T
// allowing the inheritance between this types
class ChieldGlass<T> extends SuperGlass<T> {
}

class WildGlass<T> {}

class Tray {
  private List<WildGlass<?>> trayContent = new ArrayList<>();

  void add(WildGlass<?> glass) {//this tray can have glasses of any kind even cakes
    trayContent.add(glass);
  }
  void addOnlyJuice(WildGlass<? extends Juice> glass) {//this tray can only have glasses of juices
    trayContent.add(glass);
  }
  void showContent() {
    System.out.println(trayContent);
  }
}
