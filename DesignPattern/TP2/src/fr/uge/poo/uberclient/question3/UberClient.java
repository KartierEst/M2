package fr.uge.poo.uberclient.question3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class UberClient {

    private UberClient(UberClientBuilder builder) {
    }

    public static Step.One with(){
        return new UberClientBuilder();
    }

    public static class UberClientBuilder implements Step.One, Step.Two, Step.Three, Step.Four, Step.Five, Step.Six{
        private String firstName;
        private String lastName;
        private long uid;
        private List<Integer> grades = new ArrayList<>();
        private List<String> emails = new ArrayList<>();
        private List<String> phoneNumbers = new ArrayList<>();

        @Override
        public Step.Two firstname(String firstname) {
            this.firstName = Objects.requireNonNull(firstName);
            return this;
        }

        @Override
        public Step.Three lastname(String lastName) {
            this.lastName = Objects.requireNonNull(lastName);
            return this;
        }

        @Override
        public Step.Four uid(long uid) {
            if (uid < 0) {
                throw new IllegalArgumentException("UID must be positive");
            }
            this.uid = uid;
            return this;
        }

        @Override
        public Step.Four randomUid() {
            this.uid = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
            return this;
        }

        @Override
        public Step.Five grade(int grade) {
            if (grade < 1 || grade > 5) {
                throw new IllegalArgumentException("All grades must be between 1 and 5");
            }
            grades.add(grade);
            return this;
        }

        @Override
        public Step.Five grades(List<Integer> grades) {
            Objects.requireNonNull(grades);
            for (var gr : grades) {
                this.grades.add(Objects.requireNonNull(gr));
            }
            return this;
        }

        @Override
        public Step.Six email(String email) {
            emails.add(Objects.requireNonNull(email));
            return this;
        }

        @Override
        public Step.Six phoneNumber(String phoneNumer) {
            phoneNumbers.add(Objects.requireNonNull(phoneNumer));
            return this;
        }


        // on peut supprimer les verifications car on peut mettre les méthodes à la suite avant de build
        @Override
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

    public static void main(String[] args) {
        //var arnaud = new UberClient("Arnaud","Carayol",1,List.of(1,2,5,2,5,1,1,1),List.of("arnaud.carayol@univ-eiffel.fr","arnaud.carayol@u-pem.fr"),List.of("07070707070707"));
        //var youssef = new UberClient("Youssef", "Bergeron", List.of(5), List.of("youssefbergeron@outlook.fr"),List.of());

        var test = UberClient.with().firstname("test").lastname("test");
    }

}
