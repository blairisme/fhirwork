/*
 * FHIRWork (c)
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 *
 * Author: Alperen Karaoglu
 */

const express = require('express');
const router = express.Router();

//Get for the main page, renders the template for the main page
router.get('/', function(req, res){
    res.render('mainTemplate');
});

module.exports = router;
