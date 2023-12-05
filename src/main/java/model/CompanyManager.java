package model;
import java.util.ArrayList;
import java.util.UUID;
/**
 * Manage company objects and provides method for manipulating them
 * @author Gavin Hewitt
 * @version "v1.0"
 * Date: 10/15/23
 */
public class CompanyManager {
  private static CompanyManager companyManager;
  private static ArrayList<Company> companies;

  private CompanyManager() {
    companies = new ArrayList<Company>();
  }
  /**
   * retrieves the singleton instance of the CompanyManager class
   * @return CompanyManager instance
   */
  public static CompanyManager getInstance() {
    if(companyManager == null)
      companyManager = new CompanyManager();
    return companyManager;
  }

  /**
   * Checks if Company to be added is present in list and a valid company and
   * adds to the list of Companies if true.
   * @param company Company to be added
   * @return boolean if successfully added
   */
  public boolean addCompany(Company company) {
    for(Company currCompany : companies) {
      if(currCompany.equals(company))
        return false;
    }
    companies.add(company);
    AppFacade.getInstance().setActiveCompany(company);
    AppFacade.getInstance().getActiveUser().addCompany(company);
    return true;
  }

  /**
   * Iterates through ArrayList of Company objects and removes the specified
   * Company if present.
   * @param company Company to be removed
   * @return Company removed
   */
  public Company removeCompany(Company company) {
    for(Company currCompany : companies) {
      if(currCompany.equals(company)) {
        companies.remove(currCompany);
        return currCompany;
      }
    }
    return null;
  } 
  /**
     * Retrieves company object by its unique ID.
     *
     * @param id The UUID of the Company to be retrieved
     * @return The company with the specified ID, or null if not found
     */
  public Company getCompany(UUID id) {
    for(Company company : companies) {
      if(id.equals(company.getID()))
        return company;
    }
    return null;
  }
    /**
     * Retrieves a Company object by its name
     * @param name name of the company to be retrieved
     * @return company with the specified name, or null if not found
     */
  public Company getCompany(String name) {
    for(Company company : companies) {
      if(name.toLowerCase().equals(company.getName().toLowerCase()))
        return company;
    }
    return null;
  }
  /**
     * gets the list of all companies managed by this CompanyManager.
     *
     * @return ArrayList of Company objects
     */
	public ArrayList<Company> getCompanies() {
		return companies;
	}
  /**
   * saves the list of companies to persistent storage.
   * @return true if the companies are successfully saved, false otherwise
   */
  public boolean saveCompanies() {
    return DataWriter.saveCompanies();
  }
}