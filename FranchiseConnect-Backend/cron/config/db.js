const mongoose = require('mongoose');

const connectDB = async () => {
    try {
        // Use the URI from .env file, fallback to local if not found
        const uri = process.env.MONGO_URI || 'mongodb://127.0.0.1:27017/franchiseDB';
        await mongoose.connect(uri);
        console.log("✅ MongoDB Connected to Atlas!");
    } catch (err) {
        console.error("❌ MongoDB Connection Error:", err.message);
        process.exit(1);
    }
};

module.exports = connectDB;
