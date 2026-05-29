const express = require('express');
const router = express.Router();
const brandController = require('../controllers/brandController');

router.get('/', brandController.getBrands);
router.post('/', brandController.createBrand);
router.get('/seed', brandController.seedBrands);

module.exports = router;
