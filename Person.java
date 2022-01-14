public class Person {
    String firstName, lastName, email, phone;

    Person(String details){//create person from comma-separated string
        String d[] = details.split(",");
        firstName = d[0];
        lastName = d[1];
        email = d[2];
        phone = d[3];
    }
    Person(String f, String l, String e, String p){//create person from fields
        firstName = f;
        lastName = l;
        email = e;
        phone = p;
    }
    public void print(){//print person info to console
        System.out.println("Name: "+firstName+" "+lastName);
        System.out.println("Email: "+email);
        System.out.println("Phone #: "+phone);
    }
    public String save(){//create string to save to file
        return firstName + "," + lastName + ","+ email + ","+ phone + "\n";
    }
    public boolean searchText(int filter, String text){//searches for match
        boolean result = false;
        switch (filter) {
            case 1://check first name
                if(firstName.toLowerCase().contains(text.toLowerCase())){
                    result = true;
                }
                break;
            case 2://check last name
                if(lastName.toLowerCase().contains(text.toLowerCase())){
                    result = true;
                }
                break;
            case 3://check email
                if(email.toLowerCase().contains(text.toLowerCase())){
                    result = true;
                }
                break;
            case 4://check phone (strip spaces, hyphens and parentheses)
                if(phone.replace("-", "").replace("(", "").replace(")", "").replace(" ", "").toLowerCase().contains(text.replace("-", "").replace("(", "").replace(")", "").replace(" ", "").toLowerCase())){
                    result = true;
                }
                break;
            case 5://search entire record
                if(save().toLowerCase().contains(text.toLowerCase())){
                    result = true;
                }
                break;
            default:
                break;
        }
        return result;
    }
    public void edit(int field, String newData){//edit field
        switch (field) {
            case 1:
                firstName = newData;
                break;
            case 2:
                lastName = newData;
                break;
            case 3:
                email = newData;
                break;
            case 4:
                phone = newData;
                break;
            default:
                break;
        }
    }
}
