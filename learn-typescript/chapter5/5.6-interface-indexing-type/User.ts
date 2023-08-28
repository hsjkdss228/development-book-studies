interface User {
  id: string;
  name: string;
  [property: string]: string;
  [index: number]: string;
}

export default User;
