const mongoose = require('mongoose');

const ReviewSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    brandId: { type: mongoose.Schema.Types.ObjectId, ref: 'Brand', required: true },
    rating: { type: Number, min: 1, max: 5 },
    comment: String
}, { timestamps: true });

module.exports = mongoose.model('Review', ReviewSchema);
