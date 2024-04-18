export interface User {
    id: number;
    username: string;
    firstname: string;
    lastname: string;
    email: string;
    rol: {
        id: number;
        name: string;
    };
}
