using Sqwads.Application.Interfaces.Data.Repositories;
using Sqwads.Domain.Squads;

namespace Sqwads.Infrastructure.Data.Repos;

public class SquadRepo : IRepo<Squad>
{
    public void Add(Squad entity)
    {
        throw new NotImplementedException();
    }

    public void AddRange(IEnumerable<Squad> entities)
    {
        throw new NotImplementedException();
    }

    public Task<Squad?> GetByIdAsync(int id)
    {
        throw new NotImplementedException();
    }

    public void Remove(Squad entity)
    {
        throw new NotImplementedException();
    }

    public void RemoveRange(IEnumerable<Squad> entities)
    {
        throw new NotImplementedException();
    }

    public void StartTracking(Squad entity)
    {
        throw new NotImplementedException();
    }

}
