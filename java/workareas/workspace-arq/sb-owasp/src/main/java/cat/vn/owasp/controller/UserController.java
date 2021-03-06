package cat.vn.owasp.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cat.vn.owasp.model.User;
import cat.vn.owasp.repository.UserRepository;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;
	
	@ApiOperation(value="Devuelve todos los usuarios")
	@GetMapping(path = "/all")
	public @ResponseBody List<User> getAllUsers() {
		log.info("Busqueda de todos los usuarios");
		return userRepository.findAll();
	}

	@ApiOperation(value="Devuelve el usuario asociado al username recibido")
	@GetMapping(path = "/{id}")
	public @ResponseBody Optional<User> getUserByUserId(@PathVariable("id") Integer id) {
		log.info("Busqueda del usuario " + id);
		return userRepository.findById(id);
	}
	
	@ApiOperation(value="Inserta un usuario (o lo modifica si username ya existe) a partir de los datos recibidos. NO requiere id")
	@PostMapping(path = "/add")
	public @ResponseBody String addUser(@RequestParam String username, @RequestParam String password) {
		log.info("Insertando o modificando usuario con username: " + username);
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		userRepository.save(user);
		log.info("Usuario añadido");
		return "Usuario añadido";
	}
	
	@ApiOperation(value="Elimina el usuario asociado al id recibido")
	@PostMapping(path = "/delete/{id}")
	public @ResponseBody String deleteUser(@PathVariable("id") Integer id) {
		log.info("Elimina usuario con id: " + id);
		userRepository.deleteById(id);
		log.info("Usuario eliminado");
		return "Usuario eliminado";
	}
	
	@ApiOperation(value="Elimina el usuario asociado al id recibido")
	@PostMapping(path = "/deleteByUsername/{username}")
	public @ResponseBody String deleteUserByUsename(@PathVariable("username") String username) {
		log.info("Elimina usuario con username: " + username);
		User user = userRepository.findByUsername(username);
		userRepository.delete(user);
		log.info("Usuario eliminado");
		return "Usuario eliminado";
	}

}
