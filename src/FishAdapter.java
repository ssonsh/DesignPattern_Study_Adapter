public class FishAdapter implements Animal{

    private final Fish fish;

    public FishAdapter(Fish fish) {this.fish = fish;}

    @Override
    public void walk() {
        System.out.println("\t\t adapted Fish!!");
        fish.swim();
    }
}
