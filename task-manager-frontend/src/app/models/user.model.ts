export interface UserDTO {
  id: number;
  fullName: string;
  email: string;
  createdDate: string;
  updatedDate: string;
  enabled: boolean;
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
  username: string;
  authorities: string[];
}

export interface RegisterUserDTO {
  fullName: string;
  email: string;
  password: string;
}

export interface LoginUserDTO {
  email: string;
  password: string;
}

export interface LoginResponseDTO {
  token: string;
  expirationInSeconds: number;
}
