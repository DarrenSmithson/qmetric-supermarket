# Notes

Possibly create an enum set rather than 1 large enum for discounts.

Add a name to item or product to ensure discounts arent applied to non discounted items that are in the basket, groupby in the stream api could then be used to total discounts by item name and thus remove the need for a set containing unique discoutStrategys to sum the total discount (which is currently used). In hindsight using name would seem teh correct choice as a product needs a name and a named product would have 1 disountStrategy applied e.g all tins of beans 2 for 1 etc.

Possibly split the tests as basket test now streams over 20 tests and tidy naming of tests.
