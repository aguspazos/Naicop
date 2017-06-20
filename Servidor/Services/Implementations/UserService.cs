using Entitites;
using Entitites.Exceptions;
using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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
        public User CreateUser(User user)
        {
            IEnumerable<User> existingUsers = unitOfWork.UserRepository.Get((u => u.Email == user.Email));
            if (existingUsers.Count() > 0) {
                throw new UserException("Ya existe un usuario con ese email");
            }else
            {
                unitOfWork.UserRepository.Insert(user);
                user.Token = Guid.NewGuid().ToString();
                unitOfWork.Save();
                return user;
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
    }

}
