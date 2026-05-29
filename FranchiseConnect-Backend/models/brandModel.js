const mongoose = require('mongoose');

const BrandSchema = new mongoose.Schema({
    name: String,
    category: String,
    investment: String,
    logoUrl: String
});

module.exports = mongoose.model('Brand', BrandSchema);
