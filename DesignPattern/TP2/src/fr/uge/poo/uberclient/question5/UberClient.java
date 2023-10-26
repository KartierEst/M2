package fr.uge.poo.uberclient.question5;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class UberClient {

    private String firstName;
    private String lastName;
    private long uid;
    private List<Integer> grades = new ArrayList<>();
    private List<String> emails = new ArrayList<>();
    private List<String> phoneNumbers = new ArrayList<>();

    private UberClient(UberClientBuilder builder) {
        this.firstName = builder.firstName;
        this.uid = builder.uid;
        this.phoneNumbers = builder.phoneNumbers;
        this.grades = builder.grades;
        this.lastName = builder.lastName;
        this.emails = builder.emails;
    }

    private UberClientInfo infos(){
        return new UberClientInfo(firstName,lastName);
    }

    double getScore(ScoreStrategy strategy){
        return strategy.computeScore(grades);
    }

    private UberClientInfo infosWithAverage(int limit, boolean email){
        var score = limit == 0 ? getScore(new AverageStrategy()) : getScore(new AverageLimitStrategy(limit));
        return new UberClientInfo(firstName,lastName,score,email ? emails : null);
    }

    public String export(UberClientFormatter formatter, boolean average, boolean email, int limit) {
        return average ? formatter.formatHtml(infosWithAverage(limit,email),limit) : formatter.toHTMLSimple(infos());
    }

    public static UberClientBuilder with(){
        return new UberClientBuilder();
    }

    public static class UberClientBuilder {
        private String firstName;
        private String lastName;
        private long uid;
        private List<Integer> grades = new ArrayList<>();
        private List<String> emails = new ArrayList<>();
        private List<String> phoneNumbers = new ArrayList<>();

        public UberClientBuilder firstName(String firstName) {
            this.firstName = Objects.requireNonNull(firstName);
            return this;
        }

        public UberClientBuilder lastname(String lastName) {
            this.lastName = Objects.requireNonNull(lastName);
            return this;
        }

        public UberClientBuilder uid(long uid) {
            if (uid < 0) {
                throw new IllegalArgumentException("UID must be positive");
            }
            this.uid = uid;
            return this;
        }

        public UberClientBuilder randomUid() {
            this.uid = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
            return this;
        }

        public UberClientBuilder grade(int grade) {
            if (grade < 1 || grade > 5) {
                throw new IllegalArgumentException("All grades must be between 1 and 5");
            }
            grades.add(grade);
            return this;
        }

        public UberClientBuilder grades(List<Integer> grades) {
            Objects.requireNonNull(grades);
            for (var gr : grades) {
                this.grades.add(Objects.requireNonNull(gr));
            }
            return this;
        }

        public UberClientBuilder email(String email) {
            emails.add(Objects.requireNonNull(email));
            return this;
        }

        public UberClientBuilder phoneNumber(String phoneNumer) {
            phoneNumbers.add(Objects.requireNonNull(phoneNumer));
            return this;
        }
        
        public UberClient build() {
            if (firstName == null || lastName == null || uid < 0) {
                throw new IllegalStateException("firstname or lastname or uid are mandatory");
            }
            if (grades.size() == 0) {
                throw new IllegalArgumentException("A client must have at least one grade");
            }
            if (emails.size() == 0 && phoneNumbers.size() == 0) {
                throw new IllegalArgumentException("A client must have at least an email or a phoneNumber");
            }
            return new UberClient(this);
        }
    }

    // beaucoup de duplications + single responsibility n'est pas respecté car on fait aussi de l'exportation ici

    public static void main(String[] args) {
        //var arnaud = new UberClient("Arnaud","Carayol",1,List.of(1,2,5,2,5,1,1,1),List.of("arnaud.carayol@univ-eiffel.fr","arnaud.carayol@u-pem.fr"),List.of("07070707070707"));
        //var youssef = new UberClient("Youssef", "Bergeron", List.of(5), List.of("youssefbergeron@outlook.fr"),List.of());


        UberClient arnaud = new UberClientBuilder().firstName("Arnaurd").lastname("Carayol").uid(1)
                .grade(1).grade(2).grade(5).grade(2).grade(1).grade(5).grade(2).grade(1)
                .email("arnaud.carayol@univ-eiffel.fr").email("arnaud.carayol@u-pem.fr").phoneNumber("07070707070707").build();

        UberClient youssef = new UberClientBuilder().firstName("Youssef").lastname("Bergeron").randomUid()
                .grade(5).email("youssefbergeron@outlook.fr").build();

        //UberClient test = UberClient.with().build();

        var htmlformat = new HTMLFormat();

        System.out.println(arnaud.export(htmlformat,true,false,0));
        System.out.println(arnaud.export(htmlformat,false,false, 0));
        System.out.println(arnaud.export(htmlformat,true,false,7));
        System.out.println(arnaud.export(htmlformat,true,true,0));
        System.out.println(arnaud.export(htmlformat,true,true,5));
    }

}
