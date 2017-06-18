using Entitites;
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

        public User CreateUser(User user)
        {
            unitOfWork.UserRepository.Insert(user);
            unitOfWork.Save();
            return user;
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
