package murder;



import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.cat.jaumebalmes.eltest.models.User;
import org.cat.jaumebalmes.eltestjb.models.persist.DBConnect;
import org.cat.jaumebalmes.eltestjb.models.persist.UserDao;

/**
 *
 * @author Zaid
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/loginservlet"})
public class LoginServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        try {
            DBConnect.loadDriver();
            userDao = new UserDao();
        } catch (ClassNotFoundException ex) {
            System.out.println("Error con la conexión de la base de datos");
        }
        
    }

    
    
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //String action = request.getParameter("action");

        //if (action != null) {
            //if (action.equals("login")) {
                //login(request, response);
                //doPost(request, response);
            //}
        //}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String mail = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            
            PrintWriter out = response.getWriter();
            if (mail != null) {

                User user = userDao.findUserByEmail(mail);

                if (user != null) {

                    if (user.getPassword().equals(password)) {
                        HttpSession session = request.getSession(true);
                        session.setAttribute("user", user);
                        //request.getRequestDispatcher("userservlet").forward(request, response);
                        RequestResult result = new RequestResult(user);
                        
                        out.write(new Gson().toJson(result));
                    } else {
                        RequestResult result = new RequestResult("Incorrect password");
                        
                        out.write(new Gson().toJson(result));
                    }

                } else {
                    RequestResult result = new RequestResult("This email is not in the database");
                    out.write(new Gson().toJson(result));
                }

            } else {
                RequestResult result = new RequestResult("An unexpected error has occurred");
                out.write(new Gson().toJson(result));
            }
        } catch (Exception e) {
        }
    
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void login(HttpServletRequest request, HttpServletResponse response) {
    }
}
