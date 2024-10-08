package com.assignment.user.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	private Type type;
	private String name;
	
	@PrePersist
    public void perPersists(){
//        name = "ROLE_".concat(name);
		name = name.toUpperCase();
    }

    @Override
    public String toString(){
        return name;
    }
    
    public enum Type {
    	ADMIN, EMPLOYEE, USER
    }

}
