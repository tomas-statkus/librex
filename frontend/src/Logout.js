import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Logout() {
    const navigate = useNavigate();

    useEffect(() => {
        const logout = async () => {
            try {
                const response = await fetch("http://localhost:8080/logout", {
                    method: "POST",
                    credentials: 'include'
                });
                if (response.ok) {
                    console.log("✅ Logout successful");
                } else {
                    console.error("❌ Logout failed");
                }
            } catch (error) {
                console.error("❌ Logout error:", error);
            } finally {
                navigate('/');
                window.location.reload();  // force a page reload
            }
        };
        logout();
    }, [navigate]);

    return null;  // Optionally return a loading indicator
}