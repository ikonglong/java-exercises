But how do we decide which wildcards to use?

- When should we use the extends wildcard?

- When should we use the super wildcard?

- Where is it inappropriate to use a wildcard at all?

Fortunately, there’s a simple principle that helps us make decision easily. It’s the Get and Put Principle.

This principle states that:

    Use an extends wildcard when you only get values out of a structure.
    Use a super wildcard when you only put values into a structure.
    And don’t use a wildcard when you both get and put.
    
And there are two exceptions:

    You cannot put anything into a type declared with an extends wildcard except for the value null, which belongs to every reference type.
    You cannot get anything out from a type declared with a super wildcard except for a value of type Object, which is a super type of every reference type.