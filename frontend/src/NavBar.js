import { Link } from "react-router-dom";
import "./App.css";

function NavBar({ isAuthenticated }) {
    return (
        <nav className="navbar">
            <div className="nav-left">
                <Link to="/" className="nav-link">HOME</Link>
            </div>

            <div className="nav-right">
                {isAuthenticated ? (
                    <>
                        <span className="auth-status success">LOGGED IN AS ADMIN</span>
                        <Link to="/admin" className="nav-link">ADMIN PANEL</Link>
                        <Link to="/logout" className="nav-link logout">LOGOUT</Link>
                    </>
                ) : (
                    <Link to="/login" className="nav-link login">LOGIN</Link>
                )}
            </div>
        </nav>
    );
}

export default NavBar;
