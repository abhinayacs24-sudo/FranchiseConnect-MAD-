const mongoose = require('mongoose');

const InterestSchema = new mongoose.Schema({
    userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    brandId: { type: mongoose.Schema.Types.ObjectId, ref: 'Brand', required: true },
    status: { type: String, enum: ['pending', 'contacted', 'closed'], default: 'pending' }
}, { timestamps: true });

module.exports = mongoose.model('Interest', InterestSchema);
