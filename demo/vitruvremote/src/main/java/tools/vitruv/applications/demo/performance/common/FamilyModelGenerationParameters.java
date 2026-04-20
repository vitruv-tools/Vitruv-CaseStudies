package tools.vitruv.applications.demo.performance.common;

public class FamilyModelGenerationParameters{
    private int noFamilies;
    private int noMembersPerFamily;

    public FamilyModelGenerationParameters(int noFamilies, int noMembersPerFamily) {
        this.noFamilies = noFamilies;
        this.noMembersPerFamily = noMembersPerFamily;
    }

    public int noFamilies() {
        return noFamilies;
    }

    public int noMembersPerFamily() {
        return noMembersPerFamily;
    }
}
