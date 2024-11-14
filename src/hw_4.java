import java.util.Random;

public class hw_4 {
    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 200}; // Добавили здоровье медика
    public static int[] heroesDamage = {20, 15, 10, 0}; // Медик не атакует, его урон равен 0
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic"};
    public static int medicHealingPower = 30; // Сила лечения медика
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        heroesAttack();
        medicHeal(); // Медик лечит после атак героев
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length - 1); // 0,1,2 (без медика)
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0 && !heroesAttackType[i].equals("Medic")) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i].equals(bossDefence)) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = damage * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void medicHeal() {
        if (heroesHealth[3] <= 0) return; // Если медик мертв, он не лечит

        for (int i = 0; i < heroesHealth.length; i++) {
            if (i != 3 && heroesHealth[i] > 0 && heroesHealth[i] < 100) { // Медик лечит только живых и кроме себя
                heroesHealth[i] += medicHealingPower;
                System.out.println("Medic heals " + heroesAttackType[i] + " на " + medicHealingPower + " единиц.");
                break; // Лечит только одного героя за раунд
            }
        }
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}

