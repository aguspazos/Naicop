using Entitites;
using Entitites.Exceptions;
using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils;

namespace Services.Implementations
{
    public class UserService :IUserService
    {
        private IUnitOfWork unitOfWork;

        public UserService()
        {
            this.unitOfWork = new UnitOfWork();
        }
        public UserService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public User Login(string email,string password)
        {
            IEnumerable<User> users = unitOfWork.UserRepository.Get((u => u.Email == email && u.Password.Equals(password)));

            if(users.Count() > 0)
            {
                string token = Guid.NewGuid().ToString();
                User user = users.First();
                user.Token = token;
                unitOfWork.Save();

                return user;
            }
            return null;
        }

        public List<User> GetAll()
        {
            return unitOfWork.UserRepository.Get().ToList();
        }

        public User FacebookLogin(User newUser)
        {
            IEnumerable<User> users = unitOfWork.UserRepository.Get((u => u.Email == newUser.Email || u.FacebookID == newUser.FacebookID));
            if (users.Count() > 0)
            {

                string token = Guid.NewGuid().ToString();
                User user = users.First();
                user.FacebookID = newUser.FacebookID;
                user.Token = token;
                unitOfWork.Save();
                return user;
            }
            else {
                return CreateUser(newUser);
            }


        }
        public User CreateUser(User user)
        {
            IEnumerable<User> existingUsers = unitOfWork.UserRepository.Get((u => u.Email == user.Email));
            if (existingUsers.Count() > 0) {
                throw new UserException("Ya existe un usuario con ese email");
            } else
            {
                validateUser(user);
                unitOfWork.UserRepository.Insert(user);
                user.Token = Guid.NewGuid().ToString();
                unitOfWork.Save();
                return user;
            }

        }

        public User UpdateUser(string token , User user)
        {
            User existingUser = GetFromToken(token);
            if (existingUser == null)
                throw new UserException("No existe el usuario a acutalizar");

            existingUser.Email = user.Email;
            existingUser.Phone = user.Phone;
            existingUser.Name = user.Name;
            existingUser.LastName = user.LastName;

            validateUser(existingUser);
            unitOfWork.UserRepository.Update(existingUser);
            unitOfWork.Save();

            return existingUser;
        }
        private void validateUser(User user)
        {
            if (user.FacebookID == null || user.FacebookID.Equals("0") || user.FacebookID.Equals(""))
            {
                IsValidUser(user);
            }
            
        }

        public User GetFromToken(string token)
        {
            IEnumerable<User> users = unitOfWork.UserRepository.Get((u=>u.Token == token));
            if (users.Count() > 0)
            {
                return users.First();
            }
            return null;
        }

        public bool IsValidUser(User user)
        {

            if (!HelperFunctions.IsValidEmail(user.Email))
                throw new UserException("El formato del email es inválido");
            if (!IsValidPassword(user.Password))
                throw new UserException("La contraseña debe ser de 6 caracteres o más");
            if (!HelperFunctions.IsEmptyString(user.Name))
                throw new UserException("Nombre no puede ser vacío");
            if (!HelperFunctions.IsEmptyString(user.LastName))
                throw new UserException("Apellido no puede ser vacío");
            if (!HelperFunctions.IsEmptyString(user.Name))
                throw new UserException("Teléfono no puede ser vacío");
            return true;
        }

        private bool IsValidPassword(string password)
        {
            return password.Count() > User.PASSWORD_MIN_LENGHT;
        }
    }

}
