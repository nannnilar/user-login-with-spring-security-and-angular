
export interface UserRole {
  id?: number
  type: Type
  name: string
}

enum Type {
  ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_USER
}
