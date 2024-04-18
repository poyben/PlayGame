export interface User {
    id: number;
    username: string;
    firstname: string;
    lastname: string;
    rol: {
        id: number;
        name: string;
    };
}
