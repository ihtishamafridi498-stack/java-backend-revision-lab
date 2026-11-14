package model;

    public class DVD extends LibraryItem{

        private final int durationMinutes;

        public DVD(int id, String title,int durationMinutes){
            super(id, title);
            if(durationMinutes<=0){
                throw new IllegalArgumentException("duration minutes must be greater than zero");
            }
            this.durationMinutes=durationMinutes;
        }

        public int getDurationMinutes() {
            return durationMinutes;
        }

        @Override
        public int getLoanDays() {
            return 3;
        }

        @Override
        public String getItemType() {
            return "DVD";
        }

        @Override
        public String toString() {
            return super.toString()+"duration Minutes{" +
                    "duration Minutes=" + durationMinutes +
                    '}';
        }
    }

