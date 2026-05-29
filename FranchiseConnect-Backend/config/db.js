const mongoose = require('mongoose');
const Brand = require('../models/brandModel');

const connectDB = async () => {
    try {
        const uri = process.env.MONGO_URI || 'mongodb://127.0.0.1:27017/franchiseDB';
        await mongoose.connect(uri);
        console.log("✅ MongoDB Connected successfully!");

        // Auto-seed sample brands if none exist
        const count = await Brand.countDocuments();
        if (count === 0) {
            console.log("🌱 Database is empty. Auto-seeding sample brands...");
            const sampleBrands = [
                { name: "Burger King", category: "Food & Beverages", investment: "₹1.5Cr - 3Cr", logoUrl: "https://logo.clearbit.com/burgerking.com" },
                { name: "Lakme Salon", category: "Beauty & Salon", investment: "₹50L - 1Cr", logoUrl: "https://logo.clearbit.com/lakme.com" },
                { name: "Tealogy", category: "Food & Beverages", investment: "₹10L - 20L", logoUrl: "https://logo.clearbit.com/tealogy.in" }
            ];
            await Brand.insertMany(sampleBrands);
            console.log("✅ Auto-seeded sample brands successfully!");
        }
    } catch (err) {
        console.error("❌ MongoDB Connection Error:", err.message);
        process.exit(1);
    }
};

module.exports = connectDB;
