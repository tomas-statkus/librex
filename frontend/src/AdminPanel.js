import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

function AdminPanel() {
    const [books, setBooks] = useState([]);
    const [logs, setLogs] = useState([]);
    const [newBook, setNewBook] = useState({ title: "", author: "", isbn: "", publicationYear: "" });
    const [newLog, setNewLog] = useState({ bookId: "", userId: "", status: "", borrowDate: "", returnDate: "" });

    useEffect(() => {
        const fetchBooks = async () => {
            const response = await axios.get("http://localhost:8080/api/books");
            setBooks(response.data);
        };

        const fetchLogs = async () => {
            const response = await axios.get("http://localhost:8080/api/borrow-logs");
            setLogs(response.data);
        };

        fetchBooks();
        fetchLogs();
    }, []);

    const addBook = async () => {
        try {
            const response = await axios.post("http://localhost:8080/api/admin/books/add", newBook, { withCredentials: true });
            setBooks([...books, response.data]);
            setNewBook({ title: "", author: "", isbn: "", publicationYear: "" });
        } catch (err) {
            console.error(err);
            alert("Error adding book");
        }
    };

    const deleteBook = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/api/admin/books/delete/${id}`, { withCredentials: true });
            setBooks(books.filter((b) => b.id !== id));
        } catch (err) {
            console.error(err);
            alert("Error deleting book");
        }
    };

    const addLog = async () => {
        try {
            const response = await axios.post("http://localhost:8080/api/admin/borrow-logs/add", newLog, { withCredentials: true });
            setLogs([...logs, response.data]);
            setNewLog({ bookId: "", userId: "", status: "", borrowDate: "", returnDate: "" });
        } catch (err) {
            console.error(err);
            alert("Error adding log");
        }
    };

    const deleteLog = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/api/admin/borrow-logs/delete/${id}`, { withCredentials: true });
            setLogs(logs.filter((l) => l.id !== id));
        } catch (err) {
            console.error(err);
            alert("Error deleting log");
        }
    };

    return (
        <div className="admin-panel">
            <h2 className="section-title">ADMIN PANEL</h2>

            <div className="admin-grid">
                {/* Left Side - Books */}
                <div className="admin-column">
                    {/* Add Book */}
                    <div className="section" style={{height: "400px", overflow: "auto"}}>
                        <h3 className="sub-title">ADD BOOK</h3><br/>
                        <div className="form-group">
                            <input type="text" placeholder="TITLE" value={newBook.title} onChange={(e) => setNewBook({ ...newBook, title: e.target.value })} />
                            <input type="text" placeholder="AUTHOR" value={newBook.author} onChange={(e) => setNewBook({ ...newBook, author: e.target.value })} />
                            <input type="text" placeholder="ISBN" value={newBook.isbn} onChange={(e) => setNewBook({ ...newBook, isbn: e.target.value })} />
                            <input type="number" placeholder="PUBLICATION YEAR" value={newBook.publicationYear} onChange={(e) => setNewBook({ ...newBook, publicationYear: e.target.value })} />
                            <button className="primary-button" onClick={addBook}>ADD BOOK</button>
                        </div>
                    </div>

                    {/* Book List */}
                    <div className="section">
                        <h3 className="sub-title">BOOK LIST</h3><br/>
                        <div className="book-list">
                            {books.map((book) => (
                                <div className="book-card" key={book.id}>
                                    {book.title} by {book.author}
                                    <button className="delete-button" onClick={() => deleteBook(book.id)}>DELETE</button>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>

                {/* Right Side - Logs */}
                <div className="admin-column">
                    {/* Add Log */}
                    <div className="section" style={{height: "400px", overflow: "auto"}}>
                        <h3 className="sub-title">ADD LOG</h3><br/>
                        <div className="form-group">
                            <input type="text" placeholder="BOOK ID" value={newLog.bookId} onChange={(e) => setNewLog({ ...newLog, bookId: e.target.value })} />
                            <input type="text" placeholder="USER ID" value={newLog.userId} onChange={(e) => setNewLog({ ...newLog, userId: e.target.value })} />
                            <input type="text" placeholder="STATUS" value={newLog.status} onChange={(e) => setNewLog({ ...newLog, status: e.target.value })} />
                            <input type="date" placeholder="BORROW DATE" value={newLog.borrowDate} onChange={(e) => setNewLog({ ...newLog, borrowDate: e.target.value })} />
                            <input type="date" placeholder="RETURN DATE" value={newLog.returnDate} onChange={(e) => setNewLog({ ...newLog, returnDate: e.target.value })} />
                            <button className="primary-button" onClick={addLog}>ADD LOG</button>
                        </div>
                    </div>

                    {/* Log List */}
                    <div className="section">
                        <h3 className="sub-title">LOGS LIST</h3><br/>
                        <div className="log-list">
                            {logs.map((log) => (
                                <div className="log-item" key={log.id}><br/>
                                    Title: {log.title} TBC<br/><br/>
                                    Author: {log.author} TBC<br/><br/>
                                    ID: {log.bookId}<br/><br/>
                                    User: {log.userId}<br/><br/>
                                    Status: {log.status}<br/><br/>
                                    Date: {log.borrowDate}<br/><br/>
                                    Return: {log.returnDate}<br/><br/>
                                    <br/><button className="delete-button" onClick={() => deleteLog(log.id)}>DELETE</button><br/><br/>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AdminPanel;
