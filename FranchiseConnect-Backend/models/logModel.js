const mongoose = require('mongoose');

const LogSchema = new mongoose.Schema({
    level: String,
    message: String,
    metadata: Object
}, { timestamps: true });

module.exports = mongoose.model('Log', LogSchema);
