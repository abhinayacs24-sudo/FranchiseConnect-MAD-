const mongoose = require('mongoose');

const ReportSchema = new mongoose.Schema({
    type: String,
    data: Object,
    generatedBy: { type: mongoose.Schema.Types.ObjectId, ref: 'User' }
}, { timestamps: true });

module.exports = mongoose.model('Report', ReportSchema);
