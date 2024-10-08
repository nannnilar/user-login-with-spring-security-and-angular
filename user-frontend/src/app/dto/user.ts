import { UserRole } from "./role"

export interface User {
  id?: number
  loginId: string
  name: string
  email: string
  address: string
  roles: UserRole[]
}
