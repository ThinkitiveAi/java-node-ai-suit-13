package HealthFirstBackend.HealthFirstProject.dto;

public class ProviderAvailabilityResponseDTO {
    private boolean success;
    private String message;
    private Data data;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }

    public static class Data {
        private String availability_id;
        private int slots_created;
        private DateRange date_range;
        private int total_appointments_available;
        // Getters and setters omitted for brevity
        public String getAvailability_id() { return availability_id; }
        public void setAvailability_id(String availability_id) { this.availability_id = availability_id; }
        public int getSlots_created() { return slots_created; }
        public void setSlots_created(int slots_created) { this.slots_created = slots_created; }
        public DateRange getDate_range() { return date_range; }
        public void setDate_range(DateRange date_range) { this.date_range = date_range; }
        public int getTotal_appointments_available() { return total_appointments_available; }
        public void setTotal_appointments_available(int total_appointments_available) { this.total_appointments_available = total_appointments_available; }

        public static class DateRange {
            private String start;
            private String end;
            // Getters and setters omitted for brevity
            public String getStart() { return start; }
            public void setStart(String start) { this.start = start; }
            public String getEnd() { return end; }
            public void setEnd(String end) { this.end = end; }
        }
    }
} 