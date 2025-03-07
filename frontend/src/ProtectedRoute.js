import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import axios from 'axios';

function ProtectedRoute({ children }) {
    const [auth, setAuth] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/api/auth/status', { withCredentials: true })
            .then((res) => {
                setAuth(res.data.authenticated);
            })
            .catch(() => {
                setAuth(false);
            });
    }, []);

    if (auth === null) {
        return <div>Loading...</div>;
    }

    return auth ? children : <Navigate to="/login" />;
}

export default ProtectedRoute;
