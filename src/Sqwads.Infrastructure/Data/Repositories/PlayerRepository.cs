using Sqwads.Application.Interfaces.Data.Repositories;
using Sqwads.Domain.Players;

namespace Sqwads.Infrastructure.Data.Repositories;

public class PlayerRepository : IRepository<Player>
{
    public PlayerRepository(ApplicationDbContext dbContext)
    {

    }

    public void Add(Player entity)
    {
        throw new NotImplementedException();
    }

    public void AddRange(IEnumerable<Player> entities)
    {
        throw new NotImplementedException();
    }

    public Task<Player?> GetByIdAsync(int id)
    {
        throw new NotImplementedException();
    }

    public void Remove(Player entity)
    {
        throw new NotImplementedException();
    }

    public void RemoveRange(IEnumerable<Player> entities)
    {
        throw new NotImplementedException();
    }

    public void StartTracking(Player entity)
    {
        throw new NotImplementedException();
    }

}
