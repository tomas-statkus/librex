import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import axios from 'axios';
import './App.css';
import AdminPanel from './AdminPanel';
import Logout from './Logout';
import NavBar from './NavBar'

function HomePage() {
    const [books, setBooks] = useState([]);
    const [borrowLogs, setBorrowLogs] = useState([]);
    const [loadingBooks, setLoadingBooks] = useState(false);
    const [loadingLogs, setLoadingLogs] = useState(false);
    const [showBooks, setShowBooks] = useState(false);
    const [showLogs, setShowLogs] = useState(false);

    // Fetch books from backend
    const fetchBooks = async () => {
        setLoadingBooks(true);
        try {
            const response = await axios.get('http://localhost:8080/api/books', { withCredentials: true });
            if (Array.isArray(response.data)) {
                setBooks(response.data);
            } else {
                console.warn('Unexpected data for books:', response.data);
                setBooks([]);
                alert('Authentication required to load books.');
            }
        } catch (error) {
            console.error('Error fetching books:', error);
            setBooks([]);
            alert('Failed to load books! Check console for details.');
        } finally {
            setLoadingBooks(false);
        }
    };

    // Fetch borrow logs from backend
    const fetchBorrowLogs = async () => {
        setLoadingLogs(true);
        try {
            const response = await axios.get('http://localhost:8080/api/borrow-logs');
            if (Array.isArray(response.data)) {
                setBorrowLogs(response.data);
            } else {
                console.warn('Unexpected response:', response.data);
                setBorrowLogs([]);
                alert('Unexpected response from server.');
            }
        } catch (error) {
            console.error('Error fetching borrow logs:', error);
            setBorrowLogs([]);
            alert('Failed to load logs! Check console for details.');
        } finally {
            setLoadingLogs(false);
        }
    };

    const toggleBooks = async () => {
        if (!showBooks) {
            await fetchBooks();
        }
        setShowBooks(prev => !prev);
    };

    const toggleLogs = async () => {
        if (!showLogs) {
            await fetchBorrowLogs();
        }
        setShowLogs(prev => !prev);
    };

    return (
        <div className="App">
            <header className="app-header">
                <h3 className="app-title">Librex</h3>
                <h3 className="app-description">Library Management System</h3>
            </header>
            <div className="main-content">
                <div className="section">
                    <h2 className="section-title mb-2">Books Collection</h2>
                    <button
                        className={`button primary-button ${loadingBooks ? 'loading' : ''}`}
                        onClick={toggleBooks}
                        disabled={loadingBooks}
                    >
                        {loadingBooks ? 'Loading...' : (showBooks ? 'Hide Books' : 'Show Books')}
                    </button><br/><br/>
                    {showBooks && (
                        <div className="book-list">
                            {books.length === 0 ? (
                                <p className="text-center">No books found in the library.</p>
                            ) : (
                                books.map(book => (
                                    <div className="book-card" key={book.id}>
                                        {/* Placeholder Book Cover */}
                                        <img
                                            src="/BookCoverTemp.jpg"
                                            alt="Book Cover"
                                            className="book-cover"
                                        />

                                        {/* Book Details */}
                                        <div className="book-details">
                                            <h3>{book.title}</h3>
                                            <p><strong>Author:</strong> {book.author}</p>
                                            <p><strong>ISBN:</strong> {book.isbn}</p>
                                            <p><strong>Publication Year:</strong> {book.publicationYear}</p>
                                        </div>
                                    </div>
                                ))
                            )}
                        </div>
                    )}
                </div>
                <div className="section">
                    <h2 className="section-title mb-2">Borrowing History</h2>
                    <button
                        className={`button primary-button ${loadingLogs ? 'loading' : ''}`}
                        onClick={toggleLogs}
                        disabled={loadingLogs}
                    >
                        {loadingLogs ? 'Loading...' : (showLogs ? 'Hide Logs' : 'Show Logs')}
                    </button><br/><br/>
                    {showLogs && (
                        <div className="log-list">
                            {borrowLogs.length === 0 ? (
                                <p className="text-center">No borrowing records found.</p>
                            ) : (
                                borrowLogs.map(log => (
                                    <div className="log-item" key={log.id}>
                                        <p><strong>Book ID:</strong> {log.bookId}</p>
                                        <p><strong>User ID:</strong> {log.userId}</p>
                                        <p><strong>Status:</strong> <span className="status">{log.status}</span></p>
                                        <p><strong>Borrowed Date:</strong> {new Date(log.borrowDate).toLocaleDateString()}</p>
                                        {log.returnDate && (
                                            <p><strong>Return Date:</strong> {new Date(log.returnDate).toLocaleDateString()}</p>
                                        )}
                                    </div>
                                ))
                            )}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

function LoginPage() {
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const [error, setError] = useState(false);

    const handleChange = (e) => {
        setCredentials({ ...credentials, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/login', new URLSearchParams(credentials), {
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                withCredentials: true
            });
            window.location.replace("/admin");
        } catch (err) {
            setError(true);
        }
    };

    return (
        <div className="login-container">
            <div className="section login-box">
                <h2 className="section-title">LOGIN</h2>
                {error && <p className="error-message">Incorrect username/password</p>}
                <form onSubmit={handleSubmit} className="login-form">
                    <input type="text" name="username" placeholder="USERNAME" onChange={handleChange} required />
                    <input type="password" name="password" placeholder="PASSWORD" onChange={handleChange} required />
                    <button type="submit" className="primary-button">LOGIN</button>
                </form>
            </div>
        </div>
    );
}

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
        return <div>Loading authentication status...</div>;
    }

    return auth ? children : <Navigate to="/login" />;
}

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        axios.get('http://localhost:8080/api/auth/status', { withCredentials: true })
            .then(res => setIsAuthenticated(res.data.authenticated))
            .catch(() => setIsAuthenticated(false));
    }, []);

    return (
        <Router>
            <NavBar isAuthenticated={isAuthenticated} />

            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/logout" element={<Logout />} />
                <Route path="/admin" element={
                    <ProtectedRoute>
                        <AdminPanel />
                    </ProtectedRoute>
                } />
            </Routes>
        </Router>
    );
}

export default App;