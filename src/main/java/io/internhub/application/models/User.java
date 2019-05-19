package io.internhub.application.models;

import javax.persistence.*;

@Entity
    @Table(name = "users")
    public class User {

        @Id @GeneratedValue
        private long id;
        @Column (nullable = false)
        private String email;
        @Column (nullable = false)
        private String username;
        @Column (nullable = false)
        private String password;
        @OneToOne(fetch = FetchType.LAZY,
                cascade =  CascadeType.ALL,
                mappedBy = "user")
        private EmployerProfile employerProfile;
        @OneToOne(fetch = FetchType.LAZY,
                cascade =  CascadeType.ALL,
                mappedBy = "user")
        private InternProfile internProfile;
        private boolean enabled;
        @OneToOne
        @JoinColumn(name = "role_id")
        private Role role;


        public User() {
        }

        public User(User copy) {
            id = copy.id; // This line is SUPER important! Many things won't work if it's absent
            email = copy.email;
            username = copy.username;
            password = copy.password;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
                    return password;
                }

        public void setPassword(String password) {
            this.password = password;
        }

        public EmployerProfile getEmployerProfile() {
            return employerProfile;
        }

        public void setEmployerProfile(EmployerProfile employerProfile) {
            this.employerProfile = employerProfile;
        }

        public InternProfile getInternProfile() {
            return internProfile;
        }

        public void setInternProfile(InternProfile internProfile) {
            this.internProfile = internProfile;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }
}

