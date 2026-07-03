package jpa.repository.demo.auth.domain;

public enum Role {

    Tecnico("Tecnico"),
    Administrador("administrador"),
    User("user");

    private String role;

    Role(String roleName) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
